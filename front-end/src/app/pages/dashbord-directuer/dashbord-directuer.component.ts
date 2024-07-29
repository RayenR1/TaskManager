import { Component,OnInit} from '@angular/core';
import { LoginService } from '../../services/login.service';
import { RoleGuard } from '../../services/role-gard.service';

@Component({
  selector: 'app-dashbord-directuer',
  templateUrl: './dashbord-directuer.component.html',
  styleUrl: './dashbord-directuer.component.css'
})
export class DashbordDirectuerComponent implements OnInit{
  constructor(private loginser:LoginService,private rolegard:RoleGuard){}
  ngOnInit(){this.rolegard.role = 'director';}

}
