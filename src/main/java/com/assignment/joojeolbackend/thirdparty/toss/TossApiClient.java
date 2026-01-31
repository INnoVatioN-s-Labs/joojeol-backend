package com.assignment.joojeolbackend.thirdparty.toss;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class TossApiClient {

    public SSLContext createSSLContext(String certPath, String keyPath) throws Exception {
        // Paths are relative to project root or absolute
        return createSSLContext(new FileSystemResource(certPath), new FileSystemResource(keyPath));
    }

    public SSLContext createSSLContext(Resource certResource, Resource keyResource) throws Exception {
        X509Certificate cert = loadCertificate(certResource);
        PrivateKey key = loadPrivateKey(keyResource);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);

        keyStore.setCertificateEntry("client-cert", cert);
        keyStore.setKeyEntry("client-key", key, "".toCharArray(), new java.security.cert.Certificate[]{cert});

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "".toCharArray());

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(kmf.getKeyManagers(), null, null);
        return context;
    }

    private X509Certificate loadCertificate(Resource resource) throws Exception {
        String content = readResource(resource)
                .replace("-----BEGIN CERTIFICATE-----", "")
                .replace("-----END CERTIFICATE-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(content);

        return (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(decoded));
    }

    private PrivateKey loadPrivateKey(Resource resource) throws Exception {
        String content = readResource(resource)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(content);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private String readResource(Resource resource) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    public String makeRequest(String url, String jsonBody, SSLContext context) throws Exception {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();

        connection.setSSLSocketFactory(context.getSocketFactory());
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input);
            os.flush();
        }

        int status = connection.getResponseCode();
        InputStream is = (status >= 200 && status < 300)
                ? connection.getInputStream()
                : connection.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        connection.disconnect();
        return response.toString();
    }
    
    public String makeBearerRequest(String url, String method, String jsonBody, SSLContext context, String bearerToken) throws Exception {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();

        connection.setSSLSocketFactory(context.getSocketFactory());
        connection.setRequestMethod(method);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        if (jsonBody != null && !jsonBody.isBlank()) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("UTF-8");
                os.write(input);
                os.flush();
            }
        }

        int status = connection.getResponseCode();
        InputStream is = (status >= 200 && status < 300)
                ? connection.getInputStream()
                : connection.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        connection.disconnect();
        return response.toString();
    }
}
