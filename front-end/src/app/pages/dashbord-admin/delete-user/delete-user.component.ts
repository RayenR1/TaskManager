import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DeleteUserService } from '../../../services/adminServices/delete-user.service';
import { NgModel } from '@angular/forms';
import { Router } from '@angular/router';
import { error } from 'console';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrl: './delete-user.component.css'
})
export class DeleteUserComponent implements OnInit{
  
  id!:any;
  password:string="";
  error:boolean=false;
  constructor(private route:ActivatedRoute,private deleteUserService:DeleteUserService,private router:Router){}
  ngOnInit(){this.id=this.route.snapshot.params["id"];}

  get condition():boolean{if(this.password==""||this.password.length<8){return true;}else{return false;}}

  delete(){
    this.deleteUserService.deleteUsers(this.id,this.password).subscribe((data:any)=>{alert(data.response);
      if(data.result){
        this.router.navigateByUrl('/dashbordAdmin/listusers');
      }
     },(error) => {
      this.error=true;
      console.error('Error occurred:', error);
    }
    );
  }
  getClassObject(f: NgModel) {
    if(f.touched){return {
      'is-valid': f.valid,'is-invalid':!f.valid
    };}
    return {};
    
  }
  

}
