<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<script>
    var voteElements = $(".review-votes");
    <c:forEach var="tmpVote" items="${votes}" varStatus="vsts">
        <c:choose>
            <c:when test="${tmpVote == 1}">
                voteElements.eq(${vsts.index}).find("> .vote > .like-button").prop("checked", true);
            </c:when>
            <c:when test="${tmpVote == 0}">
                voteElements.eq(${vsts.index}).find("> .vote > .dislike-button").prop("checked", true);
            </c:when>
        </c:choose>
    </c:forEach>

    $(":checkbox").change(function() {
        if(this.checked) {
            var otherCheckbox = $(this).parent().siblings().children("input");
            
            if(otherCheckbox.prop("checked")) { // Update other counter if needed
                otherCheckbox.prop("checked", false);
                otherCheckbox.siblings("label").children("span").text(function(i, old) {
                    return +old - 1;
                });
            }

            $(this).siblings("label").children("span").text(function(i, old) {  // add one to this counter
                return +old + 1;
            });
        } else {
            $(this).siblings("label").children("span").text(function(i, old) {
                return +old - 1;    // remove one from this counter
            });
        }
        
        var data = {
                "checkboxName": $(this).attr("name"),
                "likeChecked": $(this).parents(".review-votes").find("> .vote > .like-button").prop("checked"),
                "dislikeChecked": $(this).parents(".review-votes").find("> .vote > .dislike-button").prop("checked")
            };

        $.ajax({
            url: "${pageContext.request.contextPath}/app/voteReview",
            type: "POST",
            dataType : 'json',
            contentType: "application/json",
            data: JSON.stringify(data)
        });
    });
</script>