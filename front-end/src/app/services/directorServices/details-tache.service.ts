import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DetailsTacheService {

  apiurl = 'http://localhost:9090/api/taches/p-';

  constructor(private http:HttpClient) { }
  getTache(idp:any,idt:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl+idp+'/tache-'+idt,{ headers });
  }
}
