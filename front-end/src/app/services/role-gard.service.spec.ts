import { TestBed } from '@angular/core/testing';

import { RoleGardService } from './role-gard.service';

describe('RoleGardService', () => {
  let service: RoleGardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleGardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
