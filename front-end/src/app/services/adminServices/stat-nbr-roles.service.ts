import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StatNbrRolesService {

  apiurl = 'http://localhost:9090/api/users/calculeByRole';

  constructor(private http:HttpClient) { }
  nbrbyRole(role:string){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiurl,{accessToken:`${localStorage.getItem('loginToken')}`,role:role},{ headers });
  }
}
