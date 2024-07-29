import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent ,HttpInterceptorFn } from '@angular/common/http';
import { Injectable ,inject} from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';
import { Router } from 'express';

@Injectable()
export class LoginInterceptor implements HttpInterceptor {
  constructor(private loginService: LoginService) {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(req.url.includes('localhost:9090/api')&& !req.url.includes('check2')&& !req.url.includes('projets')&&!req.url.includes('taches')&& !req.url.includes('tasks')&&!req.url.includes('employee')&&!req.url.includes('userPerformance')){
      return next.handle(req);
    }
    const clonedRequest=req.clone({
      headers:req.headers.set('Authorization', `${localStorage.getItem('loginToken')}`)
    });
    return next.handle(clonedRequest);
    }
    
    
  }
