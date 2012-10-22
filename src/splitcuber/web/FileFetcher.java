package splitcuber.web;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import splitcuber.error.WebFetchError;

public class FileFetcher {
    public static byte[] fetchFile(URL fileURL, ContentType expectedMimeType, FileOutputStream fOut)
            throws WebFetchError {
        InputStream in = null;

        URLConnection conn;
        MessageDigest md;

        try {
            conn = fileURL.openConnection();
            conn.connect();
            if (!expectedMimeType.doesEqual(conn.getContentType())) {
                throw new WebFetchError("Content-type does not match.");
            }
            in = new DataInputStream(conn.getInputStream());
            md = MessageDigest.getInstance("SHA");
            in = new DigestInputStream(in, md);

            int data;
            while ((data = in.read()) != -1) {
                fOut.write(data);
            }

        } catch (IOException | NoSuchAlgorithmException e) {
            throw new WebFetchError(e.getMessage());
        } finally {
            try {
                if (in != null)
                    in.close();
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                throw new WebFetchError(e.getMessage());
            }
        }

        return md.digest();
    }

    public static byte[] fetchFile(URL fileURL, ContentType expectedMimeType, File fOut) throws WebFetchError {
        FileOutputStream fileStream;
        try {
            fileStream = new FileOutputStream(fOut.getPath());
        } catch (FileNotFoundException e) {
            throw new WebFetchError(e.getMessage());
        }
        return fetchFile(fileURL, expectedMimeType, fileStream);
    }

    public static String fetchText(String textURL, ContentType expectedMimeType) throws WebFetchError {
        StringBuilder out = new StringBuilder();
        String nextLine;
        URL url = null;
        URLConnection urlConn = null;
        InputStreamReader inStream = null;
        BufferedReader buff = null;

        try {
            url = new URL(textURL);
            urlConn = url.openConnection();
            urlConn.connect();
            if (!expectedMimeType.doesEqual(urlConn.getContentType())) {
                throw new WebFetchError("Content-type does not match.");
            }
            inStream = new InputStreamReader(urlConn.getInputStream());
            buff = new BufferedReader(inStream);
            while ((nextLine = buff.readLine()) != null) {
                out.append(nextLine);
            }

        } catch (MalformedURLException e) {
            throw new WebFetchError("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            throw new WebFetchError("IO problem: " + e.getMessage());
        }
        return out.toString();
    }
}
