package org.opensrp.web.controller;

import org.springframework.stereotype.Controller;

@Controller
public class ANMDetailsController {
    /*private static Logger logger = LoggerFactory.getLogger(ANMDetailsController.class.toString());
    private ANMDetailsService anmDetailsService;
    private final String opensrpSiteUrl;
    private String opensrpANMDetailsUrl;
    private UserController userController;
    private HttpAgent httpAgent;

    @Autowired
    public ANMDetailsController(ANMDetailsService anmDetailsService,
                                @Value("#{opensrp['opensrp.site.url']}") String opensrpSiteUrl,
                                @Value("#{opensrp['opensrp.anm.details.url']}") String opensrpANMDetailsUrl,
                                UserController userController, HttpAgent httpAgent) {
        this.anmDetailsService = anmDetailsService;
        this.opensrpSiteUrl = opensrpSiteUrl;
        this.opensrpANMDetailsUrl = opensrpANMDetailsUrl;
        this.userController = userController;
        this.httpAgent = httpAgent;
    }

    @RequestMapping(method = GET, value = "/anms")
    @ResponseBody
    public ResponseEntity<ANMDetailsDTO> allANMs() {
        HttpResponse response = new HttpResponse(false, null);
        try {
            String anmIdentifier = userController.currentUser().getUsername();
            response = httpAgent.get(opensrpANMDetailsUrl + "?anm-id=" + anmIdentifier);
            List<ANMDTO> anmBasicDetails = new Gson().fromJson(response.body(),
                    new TypeToken<List<ANMDTO>>() {
                    }.getType());
            ANMDetails anmDetails = anmDetailsService.anmDetails(anmBasicDetails);
            logger.info("Fetched ANM details with beneficiary count.");
            return new ResponseEntity<>(mapToDTO(anmDetails), allowOrigin(opensrpSiteUrl), HttpStatus.OK);
        } catch (Exception exception) {
            logger.error(MessageFormat.format("{0} occurred while fetching ANM Details. StackTrace: \n {1}", exception.getMessage(), ExceptionUtils.getFullStackTrace(exception)));
            logger.error(MessageFormat.format("Response with status {0} and body: {1} was obtained from {2}", response.isSuccess(), response.body(), opensrpANMDetailsUrl));
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    private ANMDetailsDTO mapToDTO(ANMDetails anmDetails) {
        List<ANMDetailDTO> anmDetailsDTO =
                with(anmDetails.anmDetails())
                        .convert(new Converter<ANMDetail, ANMDetailDTO>() {
                            @Override
                            public ANMDetailDTO convert(ANMDetail entry) {
                                return new ANMDetailDTO(entry.identifier(),
                                        entry.name(),
                                        entry.location(),
                                        entry.ecCount(),
                                        entry.fpCount(),
                                        entry.ancCount(),
                                        entry.pncCount(),
                                        entry.childCount());
                            }
                        });
        return new ANMDetailsDTO(anmDetailsDTO);
    }*/
}
