package api.utils;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;

public class GeneratePemCertificate {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Generates a dummy self-signed certificate and private key, and saves them in PEM format.
     *
     * @param certFilePath Path to save the certificate PEM file.
     * @param keyFilePath  Path to save the private key PEM file.
     * @throws Exception if any error occurs during certificate or key generation.
     */
    public static void generateDummyCertificate(String certFilePath, String keyFilePath) throws Exception {
        // Generate Key Pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Define Certificate Details
        X500Name issuer = new X500Name("CN=Dummy Cert, O=Test Org, L=San Francisco, ST=California, C=US");
        X500Name subject = issuer; // Self-signed, so issuer and subject are the same
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + (365 * 24 * 60 * 60 * 1000L)); // 1 year validity

        // Create Certificate
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer,
                serial,
                startDate,
                endDate,
                subject,
                keyPair.getPublic()
        );

        // Add Basic Constraints (optional)
        certBuilder.addExtension(
                Extension.basicConstraints,
                true,
                new org.bouncycastle.asn1.x509.BasicConstraints(true)
        );

        // Sign the Certificate
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider("BC").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = certBuilder.build(signer);
        X509Certificate certificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);

        // Save Private Key and Certificate in PEM Format
        saveToPEM(keyFilePath, keyPair.getPrivate());
        saveToPEM(certFilePath, certificate);

        System.out.println("Certificate and key generated successfully!");
    }

    // Save Object to PEM File
    private static void saveToPEM(String filename, Object object) throws IOException {
        try (JcaPEMWriter writer = new JcaPEMWriter(new FileWriter(filename))) {
            writer.writeObject(object);
        }
    }

    // Example usage
    public static void main(String[] args) {
        try {
            generateDummyCertificate("dummy-cert.pem", "dummy-key.pem");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
