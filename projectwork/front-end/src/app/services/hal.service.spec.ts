import { TestBed } from '@angular/core/testing';

import { HalService } from './hal.service';

describe('HalService', () => {
  let service: HalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
