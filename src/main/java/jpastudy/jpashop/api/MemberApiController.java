package jpastudy.jpashop.api;

import jpastudy.jpashop.domain.Member;
import jpastudy.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * v1: 요청 값으로 Member 엔티티를 직접 사용하는 경우
     *
     * 문제점
     * - 엔티티에 프레젠테이션 계층을 위한 로직이 추가되어 복잡해지고, 화면과 엔티티가 섞여 한 쪽을 바꾸면 다른 쪽이 안되는 경우도 발생할 수 있다.
     * - 엔티티가 변경되면 API 스펙도 변경된다.
     * 결론
     * - 엔티티는 핵심만 놔두고 화면 용으로 별도의 DTO 를 만들어 사용하라.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @GetMapping("api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    /**
     * v2: 요청 값을 엔티티 대신 DTO 를 받는 경우
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @PatchMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        // 변경 감지를 통한 업데이트, update 리턴은 안하는 편.
        memberService.update(id, request.getName());
        // 위 update 에서 member 객체 리턴해서 해도 되지만 영속성 유지 문제가 생긴다. 조회 한 번 더 하는 것은 성능에 비교적 적게 영향을 미치므로 아래와 같이 구현
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @GetMapping("api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        return new Result(findMembers.stream()
            .map(m -> new MemberDto(m.getName()))
            .collect(Collectors.toList()));
    }

    /**
     * Result class 로 컬렉션을 감싸서 유지보수 시 필요한 필드를 추가할 수 있다.
     */
    @Data
    @AllArgsConstructor
    static class Result {
        private List<MemberDto> data;
    }
    // 강의에서는 아래 코드였으나 경고 문구가 발생해서 위 코드로 수정
//    static class Result<T>{
//        private T data;
//    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}
