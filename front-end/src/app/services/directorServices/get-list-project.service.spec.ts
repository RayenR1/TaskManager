import { TestBed } from '@angular/core/testing';

import { GetListProjectService } from './get-list-project.service';

describe('GetListProjectService', () => {
  let service: GetListProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetListProjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
