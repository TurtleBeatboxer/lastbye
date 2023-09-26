import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { publicProfile } from './public-profile.model';

@Component({
  selector: 'jhi-public-profile',
  templateUrl: './public-profile.component.html',
  styleUrls: ['./public-profile.component.scss'],
})
export class PublicProfileComponent implements OnInit, OnDestroy {
  private sub: any;
  private id: string;
  // eslint-disable-next-line @typescript-eslint/member-ordering
  public profile: publicProfile;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id']; // (+) converts string 'id' to a number
      console.log(this.id);
      this.http.post(this.applicationConfigService.getEndpointFor('/api/profile/get/data'), this.id).subscribe(
        (res: publicProfile) => {
          console.log(res);
          this.profile = res;
        },
        error => {
          this.profile = error.error;
          console.log(error);
        }
      );
      this.http.post(this.applicationConfigService.getEndpointFor('/api/profile/publicImage'), this.id).subscribe(res => {
        console.log(res);
      });
      // In a real app: dispatch action to load the details here.
    });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
