import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonality } from '../personality.model';
import { PersonalityService } from '../service/personality.service';

@Injectable({ providedIn: 'root' })
export class PersonalityRoutingResolveService implements Resolve<IPersonality | null> {
  constructor(protected service: PersonalityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonality | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personality: HttpResponse<IPersonality>) => {
          if (personality.body) {
            return of(personality.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
