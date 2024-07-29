import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class GetListUsersService {
  apiurl = 'http://localhost:9090/api/users/show_users';

  constructor(private http:HttpClient) { }
  getListUsers(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiurl,{accessToken:`${localStorage.getItem('loginToken')}`},{ headers });
  }

}
