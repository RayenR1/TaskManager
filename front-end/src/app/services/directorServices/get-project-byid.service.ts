import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GetProjectByidService {
  apiurl = 'http://localhost:9090/api/projets/Project-';

  constructor(private http:HttpClient) { }
  getProject(id:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl+id,{ headers });
  }
}
