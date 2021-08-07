## Service Desk Rest API Documentation

## Endpoint Reports: 
----
  Returns json data about a list of product.

* **URL**

  /api/reports

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
```json
    {
        "id": 2,
        "title": "Pengaduan Kesehatan",
        "content": "Vaksin",
        "date": "2021-08-01T20:00:00Z",
        "images": "",
        "imagesContentType": "image/png",
        "location": "Cibadak, Kab. Sukabumi",
        "type": "COMPLAINT",
        "category": {
            "id": 1,
            "name": "Kesehatan",
            "description": ""
        },
        "institution": {
            "id": 1,
            "instanceName": "Puskesmas Cibadak",
            "address": "",
            "contactNumber": ""
        }
    }
```
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** 
    
    ```json
    {
    "timestamp": "2021-07-31T16:37:02.728+00:00",
    "status": 404,
    "error": "Not Found",
    "path": "/api/report"
}
    ```
    
  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```typescript:
      findAllReports(): Observable<Report[]> {
        return this.http.get<any>(environment.baseEndpoint + 'api/reports');
      }
  ```
