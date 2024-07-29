import { Component ,OnInit} from '@angular/core';
import { DetaillesUserService } from '../../../services/adminServices/detailles-user.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-details-user',
  templateUrl: './details-user.component.html',
  styleUrl: './details-user.component.css'
})
export class DetailsUserComponent implements OnInit {
  user!:any;
  
  constructor(private detaillesUserService: DetaillesUserService, private route:ActivatedRoute) {}
  ngOnInit() {this.detaillesUserService.getDetailsUsers(this.route.snapshot.params["id"]).subscribe((data:any)=>{this.user=data });}

}
