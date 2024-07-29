import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GetListProjectService {
  apiurl = 'http://localhost:9090/api/projets/allProjects';

  constructor(private http:HttpClient) { }
  getListUsers(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl,{ headers });
  }
}
