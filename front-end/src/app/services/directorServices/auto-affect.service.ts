import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AutoAffectService {

  apiurl = 'http://localhost:9090/api/tasks/autoassign-on';

  constructor(private http:HttpClient) { }
  affecter(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiurl,{ headers });
  }
}
