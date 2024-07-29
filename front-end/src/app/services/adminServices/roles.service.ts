import { Injectable } from '@angular/core';
import { HttpClient ,HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RolesService {
  constructor(private http: HttpClient) {}
  private apiurl:string = 'http://localhost:9090/api/role/check2';
  checkRoleWithAPI(): Observable<any> {
    const headers=new HttpHeaders({'Content-Type': 'application/json' });
    return this.http.get<any>( this.apiurl)
  }

}
