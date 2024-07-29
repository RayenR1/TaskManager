import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DeleteUserService {
  apiurl = 'http://localhost:9090/api/users/delete-';

  constructor(private http:HttpClient) { }
  deleteUsers(id:any,password:string){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<any>(this.apiurl+id,{
      headers: headers,
      body: {
        accessToken: `${localStorage.getItem('loginToken')}`,
        password: password
      }
    });
  }

}
