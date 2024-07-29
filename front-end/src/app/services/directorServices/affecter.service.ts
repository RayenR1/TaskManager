import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AffecterService {

  apiurl = 'http://localhost:9090/api/tasks/p-';

  constructor(private http:HttpClient) { }
  affecter(ide:any,idp:any,idt:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiurl+idp+'/t-'+idt+'/assign-'+ide,{ headers });
  }
}
