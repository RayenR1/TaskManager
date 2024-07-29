import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class DetaillesUserService {

  
  apiurl = 'http://localhost:9090/api/users/show_users-';

  constructor(private http:HttpClient) { }
  getDetailsUsers(id:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiurl+id,{accessToken:`${localStorage.getItem('loginToken')}`},{ headers });
  }

}
