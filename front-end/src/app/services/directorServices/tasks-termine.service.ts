import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TasksTermineService {

  apiurl = 'http://localhost:9090/api/projets/p-1/nbrTachesTerminees';

  constructor(private http:HttpClient) { }
  getTaskValider(idp:number){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(this.apiurl+idp+'/nbrTachesTerminees',{ headers });
  }
}
