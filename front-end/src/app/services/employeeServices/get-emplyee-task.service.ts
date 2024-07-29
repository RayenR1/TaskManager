import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GetEmplyeeTaskService {

  apiurl = 'http://localhost:9090/api/employee/user_taches';

  constructor(private http:HttpClient) { }
  getListTask(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl,{ headers });
  }

}
