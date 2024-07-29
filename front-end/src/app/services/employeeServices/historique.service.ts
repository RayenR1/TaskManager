import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HistoriqueService {

  apiurl = 'http://localhost:9090/api/employee/historique';

  constructor(private http:HttpClient) { }
  getHistorique(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl,{ headers });
  }
}
