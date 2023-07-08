#### Storing Auth Secret

create a file named `oauthsecrets.yaml` in the root directory of the project and add the following content to it:

```
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: <your-client-id>
            client-secret: <your-client-secret>
            redirect-uri: "{app-baseUrl}/oauth2/callback/google"
            client-name: email, profile
```
