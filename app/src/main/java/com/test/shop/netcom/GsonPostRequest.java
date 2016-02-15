package com.test.shop.netcom;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.test.shop.model.ResponseBase;
import com.test.shop.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Created by murlidhardaharwal on 27/10/15.
 */
public class GsonPostRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final Map<String, String> params;
    String TAG = getClass().getSimpleName();

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonPostRequest(String url, Class<T> clazz, Map<String, String> headers, Map<String, String> param,
                           Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.clazz = clazz;
        this.params = param;
        this.headers = headers;
        this.listener = listener;


        int MY_SOCKET_TIMEOUT_MS = 30000;
        this.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        LogUtils.e(TAG, " URL " + url);
        LogUtils.e(TAG, " headers " + headers);
        LogUtils.e(TAG, " params " + params);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            try {
                LogUtils.e(TAG, " URL " + json);
                Response<ResponseBase> respons = Response.success(
                        gson.fromJson(json, ResponseBase.class),
                        HttpHeaderParser.parseCacheHeaders(response));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new VolleyError(" Unknown Exception"));
        }
    }

}
