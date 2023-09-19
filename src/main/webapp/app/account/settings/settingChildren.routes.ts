import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MyBurialEditComponent } from './my-burial-edit/my-burial-edit.component';
import { MyFuneralEditComponent } from './my-funeral-edit/my-funeral-edit.component';
import { MyLastMessageEditComponent } from './my-last-message-edit/my-last-message-edit.component';
import { SecurityEditComponent } from './security-edit/security-edit.component';
import { BasicInfoEditComponent } from './basic-info-edit/basic-info-edit.component';

export const settingChildrenRoute: Routes = [
  {
    path: 'basicInfoEdit',
    component: BasicInfoEditComponent,
    data: {
      pageTitle: 'global.menu.account.basicInfoEditComponent',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'myBurialEdit',
    component: MyBurialEditComponent,
    data: {
      pageTitle: 'global.menu.account.basicInfoEditComponent',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'myFuneralEdit',
    component: MyFuneralEditComponent,
    data: {
      pageTitle: 'global.menu.account.basicInfoEditComponent',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'myLastMessage',
    component: MyLastMessageEditComponent,
    data: {
      pageTitle: 'global.menu.account.basicInfoEditComponent',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'securityEdit',
    component: SecurityEditComponent,
    data: {
      pageTitle: 'global.menu.account.basicInfoEditComponent',
    },
    canActivate: [UserRouteAccessService],
  },
];
