import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { HttpClient ,HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { access } from 'fs';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private http: HttpClient, private router: Router) {}
  private apiurl:string = 'http://localhost:9090/api/role/check';
  role : string='';
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
    const token = localStorage.getItem('loginToken');

    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    return this.checkRoleWithAPI(this.role, token).pipe(
      map(response => {
        if (response.role) {
          return true;
        } else {
          this.router.navigate(['/home']); 
          return false;
        }
      }),
      catchError((err) => {
        console.error(err);
        alert('you are not authorized to access this page')
        this.router.navigate(['/home']); 
        return of(false);
      })
    );
  }

  private checkRoleWithAPI(rolee: string, token: string): Observable<any> {
    const roleRequest={
      accessToken:token,
      role:rolee
    }
    const headers=new HttpHeaders({'Content-Type': 'application/json' });
    return this.http.post<any>( this.apiurl, roleRequest, {headers})
  }
}