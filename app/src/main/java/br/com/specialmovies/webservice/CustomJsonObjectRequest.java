package br.com.specialmovies.webservice;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brunorb on 23/12/2016.
 */

public class CustomJsonObjectRequest extends Request<JSONObject>  {
    private Response.Listener<JSONObject> response;
    private Map<String, String> params;


    public CustomJsonObjectRequest(int method, String url, Map<String, String> params, Response.Listener<JSONObject> response, Response.ErrorListener listener) {
        super(method, url, listener);
        this.params = params;
        this.response = response;
    }
    public CustomJsonObjectRequest(String url, Map<String, String> params, Response.Listener<JSONObject> response, Response.ErrorListener listener) {
        super(Request.Method.GET, url, listener);
        this.params = params;
        this.response = response;
    }

    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public Map<String, String> getHeaders() throws AuthFailureError{
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("apiKey", "Essa e minha API KEY: json object");

        return(header);
    }

    public Request.Priority getPriority(){
        return(Request.Priority.NORMAL);
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String js = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return(Response.success(new JSONObject(js), HttpHeaderParser.parseCacheHeaders(response)));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void deliverResponse(JSONObject response) {
        this.response.onResponse(response);
    }

}
