import { TestBed } from '@angular/core/testing';

import { GetProjectByidService } from './get-project-byid.service';

describe('GetProjectByidService', () => {
  let service: GetProjectByidService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetProjectByidService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
