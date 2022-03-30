package com.example.msaldemo

// Required dependencies for Android
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

// Required dependencies for MSAL
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException

// Required dependencies for Volley - Used for making HTTP requests to Graph
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

// Required dependency for working with JSON objects
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var msalApplication: ISingleAccountPublicClientApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        // Creates a Public Application using MSAL configuration for single account access
        PublicClientApplication.createSingleAccountPublicClientApplication(
            this,
            R.raw.msal_auth_config,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    msalApplication = application
                    loadAccount()
                }

                override fun onError(exception: MsalException?) {
                    print(exception.toString())
                }
            })
    }

    private fun initializeUI() {
        val btnSignIn: Button = findViewById(R.id.btn_signIn)
        val btnRemoveAccount: Button = findViewById(R.id.btn_signOut)
        val btnCallGraph: Button = findViewById(R.id.btn_callGraph)

        val msalConfig = resources.openRawResource(R.raw.msal_auth_config).bufferedReader().use { it.readText() }
        val tenantId = JSONObject(msalConfig).getJSONArray("authorities").getJSONObject(0).getJSONObject("audience")["tenant_id"]

        btnSignIn.setOnClickListener{
            // Sign the user into the device
            msalApplication!!.signIn(this, "", arrayOf("user.read"), getAuthCallback())
        }

        btnRemoveAccount.setOnClickListener{
            // Sign the user out
            msalApplication!!.signOut(object :
                ISingleAccountPublicClientApplication.SignOutCallback {
                override fun onSignOut() {
                    updateUI(null)
                    signOut()
                }

                override fun onError(exception: MsalException) {
                    displayError(exception)
                }
            })
        }

        btnCallGraph.setOnClickListener{
            // Attempt to use a token from the cache.
            // If the token has expired, a new token will be requested.
            msalApplication!!.acquireTokenSilentAsync(
                arrayOf("user.read"),
                "https://login.microsoftonline.com/$tenantId",
                getAuthCallback()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        initializeUI()
        loadAccount()
    }

    private fun loadAccount() {
        if (msalApplication == null) {
            return
        }

        // Gets the current signed in account and notifies if the account changes
         msalApplication!!.getCurrentAccountAsync(object :
            ISingleAccountPublicClientApplication.CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                updateUI(activeAccount)
            }

            override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {
                if (currentAccount == null) {
                    signOut()
                }
            }

            override fun onError(exception: MsalException) {
                val txtLog: TextView = findViewById(R.id.txt_log)
                txtLog.text = exception.toString()
            }
        })
    }

    private fun getAuthCallback(): AuthenticationCallback {
        // Callback passed with token acquisition.
        // Will either return a successful token or an exception
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                updateUI(authenticationResult.account)
                callGraphAPI(authenticationResult)
            }

            override fun onError(exception: MsalException) {
                displayError(exception)
            }

            override fun onCancel() {
                // User canceled the authentication
            }
        }
    }

    private fun callGraphAPI(authenticationResult: IAuthenticationResult) {
        // Using Volley, make an HTTP GET request to Graph
        // Include the accessToken from the authenticationResult in the
        // HTTP header in the request to Graph
        val queue = Volley.newRequestQueue(this)
        val request = object : JsonObjectRequest(
            Method.GET, "https://graph.microsoft.com/v1.0/me",
            JSONObject(),
            { response ->
                val txtLog: TextView = findViewById(R.id.txt_log)
                txtLog.text = response.toString()
                val tokenExpiration: TextView = findViewById(R.id.token_expiration)
                tokenExpiration.text = authenticationResult.expiresOn.toString()
            },
            { error ->
                displayError(error)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer "+authenticationResult.accessToken
                return headers
            }
        }
        queue.add(request)
    }

    private fun displayError(exception: Exception) {
        val txtLog: TextView = findViewById(R.id.txt_log)
        txtLog.text = exception.toString()
    }

    private fun updateUI(account: IAccount?) {
        val btnSignIn: Button = findViewById(R.id.btn_signIn)
        val btnRemoveAccount: Button = findViewById(R.id.btn_signOut)
        val btnCallGraph: Button = findViewById(R.id.btn_callGraph)
        if (account != null) {
            btnSignIn.isEnabled = false
            btnRemoveAccount.isEnabled = true
            btnCallGraph.isEnabled = true
        } else {
            btnSignIn.isEnabled = true
            btnRemoveAccount.isEnabled = false
            btnCallGraph.isEnabled = false
        }
    }

    private fun signOut() {
        val txtLog: TextView = findViewById(R.id.txt_log)
        txtLog.text = ""

        val tokenExpiration: TextView = findViewById(R.id.token_expiration)
        tokenExpiration.text = ""
    }
}
