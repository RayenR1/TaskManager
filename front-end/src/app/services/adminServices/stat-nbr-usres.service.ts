import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StatNbrUsresService {
  apiurl = 'http://localhost:9090/api/users/calculeAll';

  constructor(private http:HttpClient) { }
  statNbrUsers(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiurl,{accessToken:`${localStorage.getItem('loginToken')}`},{ headers });
  }
}
