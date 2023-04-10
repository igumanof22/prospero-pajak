# PPSDM

## Local Setup
1. clone project
1. copy/rename application-*-example.properties menjadi application-*.properties
1. sesuikan configurasinya
1. install semua dependency `./mvnw clean install -Dmaven.test.skip=true` atau install dependency yang berkaitan `./mvnw -pl [directory] clean install -Dmaven.test.skip=true`
1. untuk menjalankan `./mvnw -pl [directory] spring-boot:run`

## ci/cd
1. 1 module memiliki 1 file .gitlab-ci.yml
1. jika sudah buat .gitlab-ci.yml selanjutnya tambahkan file terbut di .gitlab-ci.yml
   1. contoh tambahkan pada bagian:
      - `[nama_module]/.gitlab-ci.yml`
      
       ```
      .dts-module:
       variables:
           MODULE: "ppsdm"
       only:
           changes:
               - "ppsdm/**/*"
      ```

      - `.gitlab-ci.yml`
   
             ```
             include:
                 - local: practitioner/.gitlab-ci.yml
             job_[nama_module]_build:
             extends:
             - .[nama_module]-module
             - .build-module
               only:
               refs:
                 - dev
    
             job_[nama_module]_deploy:
             extends:
             - .[nama_module]-module
             - .deploy-module
             only:
             refs:
             - dev
             ```#   p r o s p e r o - p a j a k  
 