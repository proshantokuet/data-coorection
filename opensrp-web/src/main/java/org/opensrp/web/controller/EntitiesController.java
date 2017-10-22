package org.opensrp.web.controller;

import org.springframework.stereotype.Controller;

@Controller
public class EntitiesController {
  /*  private static Logger logger = LoggerFactory.getLogger(EntitiesController.class.toString());
    private EntitiesService service;
    private AllOpenSRPUsers allOpenSRPUsers;

    @Autowired
    public EntitiesController(EntitiesService service, AllOpenSRPUsers allOpenSRPUsers) {
        this.service = service;
        this.allOpenSRPUsers = allOpenSRPUsers;
    }

    @RequestMapping(method = GET, value = "/entities-as-json")
    @ResponseBody
    public ResponseEntity<List<EntityDetailDTO>> allEntities(@RequestParam("anmIdentifier") String anmIdentifier) {
        try {
            List<EntityDetail> entityDetails = service.entities(anmIdentifier);
            logger.info("JSON map of all entities");
            return new ResponseEntity<>(mapToDTO(entityDetails), HttpStatus.OK);
        } catch (Exception exception) {
            logger.error(MessageFormat.format("{0} occurred while fetching ANM Details. StackTrace: \n {1}", exception.getMessage(), ExceptionUtils.getFullStackTrace(exception)));
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    private List<EntityDetailDTO> mapToDTO(List<EntityDetail> entityDetails) {
        List<EntityDetailDTO> entityDetailsDTO =
                with(entityDetails)
                        .convert(new Converter<EntityDetail, EntityDetailDTO>() {
                            @Override
                            public EntityDetailDTO convert(EntityDetail entry) {
                                return new EntityDetailDTO()
                                        .withECNumber(entry.ecNumber())
                                        .withThayiCardNumber(entry.thayiCardNumber())
                                        .withEntityID(entry.entityID())
                                        .withANMIdentifier(entry.anmIdentifier())
                                        .withEntityType(entry.entityType());
                            }
                        });
        return entityDetailsDTO;
    }*/
}
