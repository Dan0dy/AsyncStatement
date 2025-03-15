Assuming that the core banking api and temple engine api are privoded by other physical system.

Here is the overall flow chat



Using Java 23 to compile the project, then using commond "mvn spring-boot:run" to start it, 



then input the get url in the postman,  input get localhost:8080/statements?fromDate=2024-01-01&toDate=2024-06-30&accountNumber=1000000001 then the pdf will be shown in the console
![image](https://github.com/user-attachments/assets/773f0094-6885-4db7-aa49-18bc5be0f733)

![image](https://github.com/user-attachments/assets/cb31fd69-1c4d-49d9-a377-edf43044b7b0)

To verify the post data:

![image](https://github.com/user-attachments/assets/12d8a3c3-6b82-44b3-94ed-e27a72ebd506)


Here I used Post to call the core banking API because of the json input.
![image](https://github.com/user-attachments/assets/40e59665-d8d6-40fc-9691-1113bd4960a6)











