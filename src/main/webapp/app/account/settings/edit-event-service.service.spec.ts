import { TestBed } from '@angular/core/testing';

import { EditEventServiceService } from './edit-event-service.service';

describe('EditEventServiceService', () => {
  let service: EditEventServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditEventServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
