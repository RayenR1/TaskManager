import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DeleteTaskService {

  apiurl = 'http://localhost:9090/api/taches/p-';

  constructor(private http:HttpClient) { }
  deleteTask(idp:any,idt:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<any>(this.apiurl+idp+'/deleteTache-'+idt,{ headers });
  }
}
