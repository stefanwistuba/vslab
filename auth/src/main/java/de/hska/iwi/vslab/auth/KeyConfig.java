package de.hska.iwi.vslab.auth;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

//keytool -genkeypair -alias webshop-jwt -keyalg RSA -keypass webshop-pass -keystore webshop.jks -storepass webshop-pass
class KeyConfig {
    private static final String KEY_STORE_FILE = "webshop.jks";
    private static final String KEY_STORE_PASSWORD = "webshop-pass";
    private static final String KEY_ALIAS = "webshop-jwt";
    @SuppressWarnings("deprecation")
    private static KeyStoreKeyFactory KEY_STORE_KEY_FACTORY = new KeyStoreKeyFactory(
            new ClassPathResource(KEY_STORE_FILE), KEY_STORE_PASSWORD.toCharArray());
    @SuppressWarnings("deprecation")
    static final String VERIFIER_KEY_ID = new String(Base64.encode(KeyGenerators.secureRandom(32).generateKey()));

    static RSAPublicKey getVerifierKey() {
        return (RSAPublicKey) getKeyPair().getPublic();
    }

    static RSAPrivateKey getSignerKey() {
        return (RSAPrivateKey) getKeyPair().getPrivate();
    }

    @SuppressWarnings("deprecation")
    private static KeyPair getKeyPair() {
        return KEY_STORE_KEY_FACTORY.getKeyPair(KEY_ALIAS);
    }
}