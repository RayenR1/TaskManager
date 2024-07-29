import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ValiderService {

  apiurl = 'http://localhost:9090/api/employee/changeStatus/t-';

  constructor(private http:HttpClient) { }
  response(idt:any,etat:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<any>(this.apiurl+idt+'/newStatus-'+etat,{ headers });
  }
}
