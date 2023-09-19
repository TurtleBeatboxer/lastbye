import { Route } from '@angular/router';
import { AboutUsComponent } from './about-us.component';

export const aboutUsRoute: Route = {
  path: 'aboutus',
  component: AboutUsComponent,
  data: {
    pageTitle: 'About Us',
  },
};
