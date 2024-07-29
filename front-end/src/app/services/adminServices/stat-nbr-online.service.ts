import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StatNbrOnlineService {

  apiurl = 'http://localhost:9090/api/users/calculeAllOnline';

  constructor(private http:HttpClient) { }
  nbrOnline(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiurl,{accessToken:`${localStorage.getItem('loginToken')}`},{ headers });
  }
}
