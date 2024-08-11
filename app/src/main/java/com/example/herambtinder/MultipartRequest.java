package com.example.herambtinder;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class MultipartRequest extends com.android.volley.Request<String> {

    private final Response.Listener<String> listener;
    private final File file;
    private final String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

    public MultipartRequest(String url, File file, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        this.file = file;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            // Write boundary
            byteArrayOutputStream.write(("--" + boundary + "\r\n").getBytes());

            // Write content disposition and file name
            byteArrayOutputStream.write(("Content-Disposition: form-data; name=\"image\"; filename=\"" + file.getName() + "\"\r\n").getBytes());

            // Write content type
            byteArrayOutputStream.write(("Content-Type: " + getMimeType(file.getName()) + "\r\n\r\n").getBytes());

            // Write file data
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();

            // Write closing boundary
            byteArrayOutputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

            return byteArrayOutputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMimeType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (extension.equals("jpg") || extension.equals("jpeg")) {
            return "image/jpeg";
        } else if (extension.equals("png")) {
            return "image/png";
        }
        return "application/octet-stream";
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}
