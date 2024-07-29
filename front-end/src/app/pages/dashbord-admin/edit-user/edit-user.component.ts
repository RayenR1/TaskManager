import { Component ,OnInit} from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { Router } from '@angular/router';
import { DetaillesUserService } from '../../../services/adminServices/detailles-user.service';
import { ActivatedRoute } from '@angular/router';
import { UpdateUserService } from '../../../services/adminServices/update-user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.css'
})
export class EditUserComponent implements OnInit{
  user: any;
  valid!: boolean;
  admin: boolean=false;
  director: boolean=false;
  employee: boolean=false;
  id!:any;
  password:string="";
  error:boolean=false;
  password1!: string;
  password2!: string;
  equipes: string="";
  roles: string[]=[];


  constructor(private router:Router,private detaillesUserService: DetaillesUserService,private route:ActivatedRoute,private UpdateuserService:UpdateUserService) { }

  ngOnInit(): void { 
    this.detaillesUserService.getDetailsUsers(this.route.snapshot.params["id"]).subscribe((data:any)=>{this.user=data;
      for(let equipe of this.user.equipes){
        this.equipes+=equipe.nom+" | ";
      }
      for(let role of this.user.roles){
        if(role.name=="admin"){this.admin=true;}
        if(role.name=="director"){this.director=true;}
        if(role.name=="employee"){this.employee=true;}

      }

     });
    
  }
  

  getClassObject(f: NgModel) {
    if(f.touched){return {
      'is-valid': f.valid,'is-invalid':!f.valid
    };}
    return {};
    
  }
  get isAnyRoleSelected(): boolean {
    return this.admin || this.director || this.employee;
  }
  get equalpassword(): boolean {
    if(this.password1==null&&this.password2==null){return true;}else{return this.password1 === this.password2;}
    
  }
  confirmPasswordClassObject(f: NgModel) {
    if(f.touched){return {
      'is-valid': this.equalpassword,'is-invalid':!this.equalpassword
    };}
    return {};
    
  }
  
  onsubmit(f: NgForm){ 
    // submit in the form this.equipes not this.user.equipes
    if(this.password1!=null){this.user.password=this.password1;}

    if (this.director) {
      if (!this.roles.includes('director')) {
        this.roles.push('director');
      }
    } else{
      const index = this.roles.indexOf('director');
      if (index !== -1) {
        this.roles.splice(index, 1);
      }
    }
    if (this.admin) {
      if (!this.roles.includes('admin')) {
        this.roles.push('admin');
      }
    } else{
      const index = this.roles.indexOf('admin');
      if (index !== -1) {
        this.roles.splice(index, 1);
      }
    }
    if (this.employee) {
      if (!this.roles.includes('employee')) {
      this.roles.push('employee');
    }
    } else{
      const index = this.roles.indexOf('employee');
      if (index !== -1) {
        this.roles.splice(index, 1);
      }
    }
    
  
  
    
      
    
    this.UpdateuserService.updateUser(this.route.snapshot.params["id"],this.user,this.equipes,this.password,this.roles).subscribe((data:any)=>{
      this.router.navigateByUrl('/dashbordAdmin/listusers');});
  }
 


  


}
