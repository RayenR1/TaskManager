import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class DeleteProjectService {
  apiurl = 'http://localhost:9090/api/projets/delete-';

  constructor(private http:HttpClient) { }
  deleteProject(id:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<any>(this.apiurl+id,{ headers });
  }
}
