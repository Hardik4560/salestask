package com.hardik.salestask.network.service;

import android.os.Looper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.hardik.salestask.network.NetworkController;
import com.hardik.salestask.network.interfaces.Callback;
import com.hardik.salestask.network.model.WeRequest;
import com.hardik.salestask.network.model.WeResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NetworkRequest {
    private static final String TAG = NetworkRequest.class.getSimpleName();

    public static final String MESSAGE_NO_INTERNET_CONNECTION = "Oh no ! The internet connection seems to be down";
    public static final String MESSAGE_INTERNAL_SERVER_ERROR = "Damn, something is wrong";
    public static final String MESSAGE_CONNECTION_TIMEOUT = "Ufff....Waiting so long but connection timed out";
    public static final String MESSAGE_TRY_AGAIN = "Something isn't right, please try again.";
    public static final String MESSAGE_DATA_PARSING_ERROR = "Something isn't right, please report it";
    public static final long TIME_OUT = 30;

    private static final Integer MAX_RETRY = 2;

    private static NetworkRequest networkRequest;

    private WeResponse responseError;

    private JSONObject responseSuccess;

    android.os.Handler handler;

    private NetworkRequest() {
        responseError = new WeResponse();
        handler = new android.os.Handler(Looper.getMainLooper());

    }

    public synchronized static NetworkRequest getInstance() {
        /*if (networkRequest == null) {
            throw new ObjectNotInitializedException(NetworkRequest.class);
        }
        return networkRequest;*/

        return networkRequest = new NetworkRequest();
    }

    public static NetworkRequest initialize() {
        if (networkRequest == null) {
            networkRequest = new NetworkRequest();
        }
        return networkRequest;
    }

    public JSONObject getJsonRequestSync(final String uri) {
        boolean status = true;

        Log.d(TAG, "Request String = " + (String) uri);

        try {
            RequestFuture<JSONObject> future = RequestFuture.newFuture();

            JsonObjectRequest registerRequest = new JsonObjectRequest(uri, null, future, future);

            //Add the request to the queue.
            NetworkController.getInstance().addToRequestQueue(registerRequest);

            responseSuccess = future.get(TIME_OUT, TimeUnit.SECONDS);
            Log.d(TAG, "Response String = " + responseSuccess);

        } catch (InterruptedException e) {
            e.printStackTrace();

            status = false;

            responseError.setError_code(HttpURLConnection.HTTP_RESET);
            responseError.setMessage(MESSAGE_TRY_AGAIN);
        } catch (ExecutionException e) {
            e.printStackTrace();

            status = false;

            //Get the error message from the server and show a dialog.
            VolleyError error = (VolleyError) e.getCause();

            if (error != null && error instanceof NoConnectionError) {
                responseError.setError_code(HttpURLConnection.HTTP_UNAVAILABLE);
                responseError.setMessage(MESSAGE_NO_INTERNET_CONNECTION);
            } else if (error instanceof ServerError) {
                responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                responseError.setMessage(MESSAGE_INTERNAL_SERVER_ERROR);
            } else if (error.networkResponse != null) {

                responseError.setError_code(error.networkResponse.statusCode);
                responseError.setMessage(new String(error.networkResponse.data));
            } else {
                responseError.setError_code(500);
                responseError.setMessage("Inconsistent internet connection.");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            status = false;

            responseError.setError_code(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
            responseError.setMessage(MESSAGE_CONNECTION_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            status = false;

            responseError.setError_code(HttpURLConnection.HTTP_BAD_REQUEST);
            responseError.setMessage(MESSAGE_TRY_AGAIN);
        }

        if (status) {
            return responseSuccess;
        } else {
            Gson gson = new Gson();
            String responseErr = gson.toJson(responseError);
            try {
                JSONObject resJson = new JSONObject(responseErr);
                return resJson;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void getJsonRequest(final String uri, final Callback<JSONObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean status = true;

                Log.d(TAG, "Request String = " + (String) uri);

                try {
                    RequestFuture<JSONObject> future = RequestFuture.newFuture();

                    JsonObjectRequest registerRequest = new JsonObjectRequest(uri, null, future, future);

                    //Add the request to the queue.
                    NetworkController.getInstance().addToRequestQueue(registerRequest);

                    responseSuccess = future.get(TIME_OUT, TimeUnit.SECONDS);
                    Log.d(TAG, "Response String = " + responseSuccess);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_RESET);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                } catch (ExecutionException e) {
                    e.printStackTrace();

                    status = false;

                    //Get the error message from the server and show a dialog.
                    VolleyError error = (VolleyError) e.getCause();

                    if (error != null && error instanceof NoConnectionError) {
                        responseError.setError_code(HttpURLConnection.HTTP_UNAVAILABLE);
                        responseError.setMessage(MESSAGE_NO_INTERNET_CONNECTION);
                    } else if (error instanceof ServerError) {

                        responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                        responseError.setMessage(MESSAGE_INTERNAL_SERVER_ERROR);

                    } else if (error.networkResponse != null) {

                        responseError.setError_code(error.networkResponse.statusCode);
                        responseError.setMessage(new String(error.networkResponse.data));
                    } else {
                        responseError.setError_code(500);
                        responseError.setMessage("Inconsistent internet connection.");

                        error.printStackTrace();
                    }
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
                    responseError.setMessage(MESSAGE_CONNECTION_TIMEOUT);
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_BAD_REQUEST);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                }

                if (callback != null) {
                    if (status) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccessResponse(responseSuccess);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onErrorResponse(responseError);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public void postJsonRequest(final String uri, final WeRequest request, final Callback<JSONObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                boolean status = true;

                Gson gson = new Gson();
                String requestString = gson.toJson(request);
                Log.d(TAG, "Request String = " + (String) requestString);

                JSONObject jsonRequestObject = null;
                try {
                    jsonRequestObject = new JSONObject((String) requestString);

                    RequestFuture<JSONObject> future = RequestFuture.newFuture();

                    JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, uri, jsonRequestObject, future, future);

                    //Add the request to the queue.
                    NetworkController.getInstance().addToRequestQueue(registerRequest);

                    responseSuccess = future.get(TIME_OUT, TimeUnit.SECONDS);
                    Log.d(TAG, "Response String = " + responseSuccess);

                } catch (JSONException e) {
                    e.printStackTrace();

                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                    responseError.setMessage(MESSAGE_DATA_PARSING_ERROR);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_RESET);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                } catch (ExecutionException e) {
                    e.printStackTrace();

                    status = false;

                    //Get the error message from the server and show a dialog.
                    VolleyError error = (VolleyError) e.getCause();

                    if (error != null && error instanceof NoConnectionError) {
                        responseError.setError_code(HttpURLConnection.HTTP_UNAVAILABLE);
                        responseError.setMessage(MESSAGE_NO_INTERNET_CONNECTION);
                    } else if (error instanceof ServerError) {
                        responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                        responseError.setMessage(MESSAGE_INTERNAL_SERVER_ERROR);
                    } else if (error.networkResponse != null) {

                        responseError.setError_code(error.networkResponse.statusCode);
                        responseError.setMessage(new String(error.networkResponse.data));
                    } else {
                        responseError.setError_code(500);
                        responseError.setMessage("Inconsistent internet connection.");
                    }
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
                    responseError.setMessage(MESSAGE_CONNECTION_TIMEOUT);
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_BAD_REQUEST);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                }

                if (callback != null) {
                    if (status) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccessResponse(responseSuccess);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onErrorResponse(responseError);
                            }
                        });
                    }
                }
            }
        }).start();
    }


    //Sync
    public WeResponse postJsonRequestSync(String uri, final WeRequest request) {

        boolean status = true;

        Gson gson = new Gson();
        String requestString = gson.toJson(request);
        Log.d(TAG, "Request String = " + (String) requestString);

        JSONObject jsonRequestObject = null;
        try {
            jsonRequestObject = new JSONObject((String) requestString);

            RequestFuture<JSONObject> future = RequestFuture.newFuture();

            JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, uri, jsonRequestObject, future, future);

            //Add the request to the queue.
            NetworkController.getInstance().addToRequestQueue(registerRequest);

            responseSuccess = future.get(TIME_OUT, TimeUnit.SECONDS);
            Log.d(TAG, "Response String = " + responseSuccess);

        } catch (JSONException e) {
            e.printStackTrace();

            status = false;

            responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
            responseError.setMessage(MESSAGE_DATA_PARSING_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();

            status = false;

            responseError.setError_code(HttpURLConnection.HTTP_RESET);
            responseError.setMessage(MESSAGE_TRY_AGAIN);
        } catch (ExecutionException e) {
            e.printStackTrace();

            status = false;

            //Get the error message from the server and show a dialog.
            VolleyError error = (VolleyError) e.getCause();

            if (error != null && error instanceof NoConnectionError) {
                responseError.setError_code(HttpURLConnection.HTTP_UNAVAILABLE);
                responseError.setMessage(MESSAGE_NO_INTERNET_CONNECTION);
            } else if (error instanceof ServerError) {
                responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                responseError.setMessage(MESSAGE_INTERNAL_SERVER_ERROR);
            } else if (error.networkResponse != null) {

                responseError.setError_code(error.networkResponse.statusCode);
                responseError.setMessage(new String(error.networkResponse.data));
            } else {
                responseError.setError_code(500);
                responseError.setMessage("Inconsistent internet connection.");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            status = false;

            responseError.setError_code(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
            responseError.setMessage(MESSAGE_CONNECTION_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            status = false;

            responseError.setError_code(HttpURLConnection.HTTP_BAD_REQUEST);
            responseError.setMessage(MESSAGE_TRY_AGAIN);
        }

        if (status) {
            WeResponse response = new WeResponse();
            response.setSuccessResponse(responseSuccess);
            return response;
        } else {
            return responseError;

        }
    }

    //Async
    public void postJsonRequest(final String uri, final JSONObject object, final HashMap headerParams, final Callback<JSONObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                boolean status = true;

                try {
                    RequestFuture<JSONObject> future = RequestFuture.newFuture();
                    JsonObjectRequest registerRequest;

                    if (headerParams != null) {
                        registerRequest = new JsonObjectRequest(Request.Method.POST, uri, object, future, future) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                return headerParams;
                            }
                        };
                    } else {
                        registerRequest = new JsonObjectRequest(Request.Method.POST, uri, object, future, future);
                    }

                    //Add the request to the queue.
                    NetworkController.getInstance().addToRequestQueue(registerRequest);

                    responseSuccess = future.get(TIME_OUT, TimeUnit.SECONDS);
                    Log.d(TAG, "Response String = " + responseSuccess);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_RESET);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                } catch (ExecutionException e) {
                    e.printStackTrace();

                    status = false;

                    //Get the error message from the server and show a dialog.
                    VolleyError error = (VolleyError) e.getCause();

                    if (error != null && error instanceof NoConnectionError) {
                        responseError.setError_code(HttpURLConnection.HTTP_UNAVAILABLE);
                        responseError.setMessage(MESSAGE_NO_INTERNET_CONNECTION);
                    } else if (error instanceof ServerError) {
                        responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                        responseError.setMessage(MESSAGE_INTERNAL_SERVER_ERROR);
                    } else if (error.networkResponse != null) {

                        responseError.setError_code(error.networkResponse.statusCode);
                        responseError.setMessage(new String(error.networkResponse.data));
                    } else {
                        responseError.setError_code(500);
                        responseError.setMessage("Inconsistent internet connection.");
                    }
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
                    responseError.setMessage(MESSAGE_CONNECTION_TIMEOUT);
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_BAD_REQUEST);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                }

                if (callback != null) {
                    if (status) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccessResponse(responseSuccess);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onErrorResponse(responseError);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public void postFormRequest(final String uri, final WeRequest request, final Callback<JSONObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                boolean status = true;

                Gson gson = new Gson();
                String requestString = gson.toJson(request);
                Log.d(TAG, "Request String = " + (String) requestString);

                JSONObject jsonRequestObject = null;
                try {
                    jsonRequestObject = new JSONObject((String) requestString);

                    RequestFuture<String> future = RequestFuture.newFuture();

                    StringRequest registerRequest = new StringRequest(Request.Method.POST, uri, future, future) {
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded; charset=UTF-8";
                        }

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            return params;
                        }
                    };

                    //Add the request to the queue.
                    NetworkController.getInstance().addToRequestQueue(registerRequest);

                    String response = future.get(TIME_OUT, TimeUnit.SECONDS);

                    Log.d(TAG, "Response String = " + response);

                    responseSuccess = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();

                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                    responseError.setMessage(MESSAGE_DATA_PARSING_ERROR);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_RESET);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                } catch (ExecutionException e) {
                    e.printStackTrace();

                    status = false;

                    //Get the error message from the server and show a dialog.
                    VolleyError error = (VolleyError) e.getCause();

                    if (error != null && error instanceof NoConnectionError) {
                        responseError.setError_code(HttpURLConnection.HTTP_UNAVAILABLE);
                        responseError.setMessage(MESSAGE_NO_INTERNET_CONNECTION);
                    } else if (error instanceof ServerError) {
                        responseError.setError_code(HttpURLConnection.HTTP_INTERNAL_ERROR);
                        responseError.setMessage(MESSAGE_INTERNAL_SERVER_ERROR);
                    } else if (error.networkResponse != null) {

                        responseError.setError_code(error.networkResponse.statusCode);
                        responseError.setMessage(new String(error.networkResponse.data));
                    } else {
                        responseError.setError_code(500);
                        responseError.setMessage("Inconsistent internet connection.");
                    }
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
                    responseError.setMessage(MESSAGE_CONNECTION_TIMEOUT);
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;

                    responseError.setError_code(HttpURLConnection.HTTP_BAD_REQUEST);
                    responseError.setMessage(MESSAGE_TRY_AGAIN);
                }

                if (callback != null) {
                    if (status) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccessResponse(responseSuccess);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onErrorResponse(responseError);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private WeResponse getDummyWeResponse(String s) {
        WeResponse weResponse = new WeResponse();
        weResponse.setMessage(s);

        return weResponse;
    }
}