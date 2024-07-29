import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ToValidateService {

  apiurl = 'http://localhost:9090/api/employee/TaskTovalidate';

  constructor(private http:HttpClient) { }
  getTasks(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl,{ headers });
  }
}
