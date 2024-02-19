import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RecoveryQuestionFormComponent } from './recovery-question-form.component';

export const recoveryQuestionRoute: Route = {
  path: 'recovery',
  component: RecoveryQuestionFormComponent,
  data: {
    pageTitle: 'global.menu.account.recoveryQuestion',
  },
  canActivate: [UserRouteAccessService],
};
