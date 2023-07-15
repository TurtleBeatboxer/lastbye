import { Route } from '@angular/router';
import { IdeaComponent } from './idea.component';

export const ideaRoute: Route = {
  path: 'idea',
  component: IdeaComponent,
  data: {
    pageTitle: 'Idea',
  },
};
