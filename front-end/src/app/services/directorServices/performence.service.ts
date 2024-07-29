import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PerformenceService {

  apiurl = 'http://localhost:9090/api/users/userPerformance';

  constructor(private http:HttpClient) { }
  getPerformance(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl,{ headers });
  }
}
