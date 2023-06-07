import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonalityComponent } from '../list/personality.component';
import { PersonalityDetailComponent } from '../detail/personality-detail.component';
import { PersonalityUpdateComponent } from '../update/personality-update.component';
import { PersonalityRoutingResolveService } from './personality-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const personalityRoute: Routes = [
  {
    path: '',
    component: PersonalityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonalityDetailComponent,
    resolve: {
      personality: PersonalityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonalityUpdateComponent,
    resolve: {
      personality: PersonalityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonalityUpdateComponent,
    resolve: {
      personality: PersonalityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personalityRoute)],
  exports: [RouterModule],
})
export class PersonalityRoutingModule {}
